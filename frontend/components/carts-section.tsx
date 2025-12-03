"use client"

import { useState, useEffect } from "react"
import { type CartResponseDTO, cartService, productService, type ProductResponseDTO } from "@/lib/api-services"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Trash2, Plus } from "lucide-react"

export function CartsSection() {
  const [carts, setCarts] = useState<CartResponseDTO[]>([])
  const [products, setProducts] = useState<ProductResponseDTO[]>([])
  const [isLoading, setIsLoading] = useState(false)
  const [selectedCartId, setSelectedCartId] = useState<number | null>(null)
  const [selectedProductId, setSelectedProductId] = useState<number>(0)
  const [quantity, setQuantity] = useState<number>(1)

  useEffect(() => {
    loadProducts()
  }, [])

  const loadProducts = async () => {
    try {
      const data = await productService.list({ page: 0, size: 100 })
      setProducts(data.content || [])
    } catch (error) {
      console.error("Failed to load products:", error)
    }
  }

  const handleCreateCart = async () => {
    setIsLoading(true)
    try {
      const newCart = await cartService.create()
      setCarts([...carts, newCart])
      setSelectedCartId(newCart.id)
    } catch (error) {
      console.error("Failed to create cart:", error)
    } finally {
      setIsLoading(false)
    }
  }

  const handleAddItem = async () => {
    if (!selectedCartId || !selectedProductId || quantity <= 0) return

    setIsLoading(true)
    try {
      const updatedCart = await cartService.addItem(selectedCartId, {
        productId: selectedProductId,
        quantity,
      })
      setCarts(carts.map((c) => (c.id === selectedCartId ? updatedCart : c)))
      setQuantity(1)
      setSelectedProductId(0)
    } catch (error) {
      console.error("Failed to add item to cart:", error)
    } finally {
      setIsLoading(false)
    }
  }

  const handleUpdateItem = async (cartId: number, itemId: number, newQuantity: number) => {
    setIsLoading(true)
    try {
      const updatedCart = await cartService.updateItem(cartId, itemId, newQuantity)
      setCarts(carts.map((c) => (c.id === cartId ? updatedCart : c)))
    } catch (error) {
      console.error("Failed to update item:", error)
    } finally {
      setIsLoading(false)
    }
  }

  const handleDeleteItem = async (cartId: number, itemId: number) => {
    setIsLoading(true)
    try {
      const updatedCart = await cartService.deleteItem(cartId, itemId)
      setCarts(carts.map((c) => (c.id === cartId ? updatedCart : c)))
    } catch (error) {
      console.error("Failed to delete item:", error)
    } finally {
      setIsLoading(false)
    }
  }

  const handleDeleteCart = async (cartId: number) => {
    if (!window.confirm("¿Estás seguro de que deseas eliminar este carrito?")) {
      return
    }

    setIsLoading(true)
    try {
      await cartService.delete(cartId)
      setCarts((prevCarts) => prevCarts.filter((c) => c.id !== cartId))
      if (selectedCartId === cartId) {
        setSelectedCartId(null)
      }
    } catch (error) {
      console.error("Failed to delete cart:", error)
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <div className="space-y-6">
      <Card>
        <CardHeader>
          <CardTitle>Gestión de Carritos</CardTitle>
        </CardHeader>
        <CardContent>
          <Button onClick={handleCreateCart} disabled={isLoading}>
            Crear Nuevo Carrito
          </Button>
        </CardContent>
      </Card>

      {carts.length > 0 && (
        <Card>
          <CardHeader>
            <CardTitle>Carritos ({carts.length})</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-6">
              {carts.map((cart) => (
                <div key={cart.id} className="border rounded-lg p-4">
                  <div className="flex justify-between items-center mb-4">
                    <div>
                      <h3 className="font-semibold">Carrito #{cart.id}</h3>
                      <p className="text-sm text-muted-foreground">Total: ${cart.total.toFixed(2)}</p>
                    </div>
                    <div className="flex gap-2">
                      <Button
                        variant={selectedCartId === cart.id ? "default" : "outline"}
                        onClick={() => setSelectedCartId(cart.id)}
                        disabled={isLoading}
                      >
                        {selectedCartId === cart.id ? "Seleccionado" : "Seleccionar"}
                      </Button>
                      {cart.items.length === 0 && (
                        <Button
                          variant="destructive"
                          size="sm"
                          onClick={() => handleDeleteCart(cart.id)}
                          disabled={isLoading}
                          title="Solo se pueden eliminar carritos vacíos"
                        >
                          <Trash2 className="w-4 h-4" />
                        </Button>
                      )}
                    </div>
                  </div>

                  {selectedCartId === cart.id && (
                    <div className="mb-4 p-3 bg-muted rounded-lg">
                      <div className="space-y-3">
                        <select
                          value={selectedProductId}
                          onChange={(e) => setSelectedProductId(Number.parseInt(e.target.value))}
                          disabled={isLoading}
                          className="w-full px-3 py-2 border rounded-md bg-background text-foreground"
                        >
                          <option value={0}>Selecciona un producto</option>
                          {products.map((product) => (
                            <option key={product.id} value={product.id}>
                              {product.name} - ${product.price.toFixed(2)}
                            </option>
                          ))}
                        </select>
                        <Input
                          type="number"
                          placeholder="Cantidad"
                          value={quantity}
                          onChange={(e) => setQuantity(Number.parseInt(e.target.value) || 1)}
                          disabled={isLoading}
                          min="1"
                        />
                        <Button onClick={handleAddItem} disabled={isLoading || !selectedProductId} className="w-full">
                          <Plus className="w-4 h-4 mr-2" />
                          Agregar al Carrito
                        </Button>
                      </div>
                    </div>
                  )}

                  {cart.items.length === 0 ? (
                    <p className="text-muted-foreground text-center py-4">Carrito vacío</p>
                  ) : (
                    <div className="space-y-2">
                      {cart.items.map((item) => (
                        <div key={item.id} className="flex justify-between items-center p-2 bg-muted rounded">
                          <div className="flex-1">
                            <p className="font-medium text-sm">{item.productName}</p>
                            <p className="text-xs text-muted-foreground">
                              ${item.price.toFixed(2)} x {item.quantity} = ${item.subtotal.toFixed(2)}
                            </p>
                          </div>
                          <div className="flex gap-2 items-center">
                            <Input
                              type="number"
                              min="1"
                              value={item.quantity}
                              onChange={(e) => handleUpdateItem(cart.id, item.id, Number.parseInt(e.target.value) || 1)}
                              disabled={isLoading}
                              className="w-16 text-center"
                            />
                            <Button
                              variant="destructive"
                              size="sm"
                              onClick={() => handleDeleteItem(cart.id, item.id)}
                              disabled={isLoading}
                            >
                              <Trash2 className="w-4 h-4" />
                            </Button>
                          </div>
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              ))}
            </div>
          </CardContent>
        </Card>
      )}
    </div>
  )
}
