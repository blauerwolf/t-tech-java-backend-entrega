"use client"

import type React from "react"

import { useState, useEffect } from "react"
import {
  type ProductRequestDTO,
  type ProductResponseDTO,
  productService,
  categoryService,
  type Category,
} from "@/lib/api-services"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Trash2, Edit2 } from "lucide-react"

export function ProductsSection() {
  const [products, setProducts] = useState<ProductResponseDTO[]>([])
  const [categories, setCategories] = useState<Category[]>([])
  const [isLoading, setIsLoading] = useState(false)
  const [editingId, setEditingId] = useState<number | null>(null)
  const [formData, setFormData] = useState<ProductRequestDTO>({
    name: "",
    image: "",
    price: 0,
    stock: 0,
    categoryId: 0,
    description: "",
  })

  useEffect(() => {
    loadCategories()
    loadProducts()
  }, [])

  const loadCategories = async () => {
    try {
      const cats = await categoryService.list()
      setCategories(cats)
    } catch (error) {
      console.error("Failed to load categories:", error)
    }
  }

  const loadProducts = async () => {
    try {
      const data = await productService.list({ page: 0, size: 100 })
      setProducts(data.content || [])
    } catch (error) {
      console.error("Failed to load products:", error)
    }
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    if (!formData.name || !formData.categoryId) return

    setIsLoading(true)
    try {
      if (editingId) {
        await productService.update(editingId, formData)
      } else {
        await productService.create(formData)
      }
      setFormData({
        name: "",
        image: "",
        price: 0,
        stock: 0,
        categoryId: 0,
        description: "",
      })
      setEditingId(null)
      await loadProducts()
    } catch (error) {
      console.error("Failed to save product:", error)
    } finally {
      setIsLoading(false)
    }
  }

  const handleEdit = (product: ProductResponseDTO) => {
    setFormData({
      name: product.name,
      image: product.image,
      price: product.price,
      stock: product.stock,
      categoryId: product.categoryId,
      description: product.description,
    })
    setEditingId(product.id)
  }

  const handleDelete = async (id: number) => {
    if (!confirm("¿Estás seguro de que deseas eliminar este producto?")) return
    try {
      await productService.delete(id)
      await loadProducts()
    } catch (error) {
      console.error("Failed to delete product:", error)
    }
  }

  const handleCancel = () => {
    setEditingId(null)
    setFormData({
      name: "",
      image: "",
      price: 0,
      stock: 0,
      categoryId: 0,
      description: "",
    })
  }

  return (
    <div className="space-y-6">
      <Card>
        <CardHeader>
          <CardTitle>{editingId ? "Editar Producto" : "Crear Nuevo Producto"}</CardTitle>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-4">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <Input
                placeholder="Nombre del producto"
                value={formData.name}
                onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                disabled={isLoading}
              />
              <Input
                placeholder="URL de la imagen"
                value={formData.image}
                onChange={(e) => setFormData({ ...formData, image: e.target.value })}
                disabled={isLoading}
              />
              <Input
                type="number"
                placeholder="Precio"
                value={formData.price}
                onChange={(e) => setFormData({ ...formData, price: Number.parseFloat(e.target.value) })}
                disabled={isLoading}
                step="0.01"
              />
              <Input
                type="number"
                placeholder="Stock"
                value={formData.stock}
                onChange={(e) => setFormData({ ...formData, stock: Number.parseInt(e.target.value) })}
                disabled={isLoading}
              />
              <select
                value={formData.categoryId}
                onChange={(e) => setFormData({ ...formData, categoryId: Number.parseInt(e.target.value) })}
                disabled={isLoading}
                className="px-3 py-2 border rounded-md bg-background text-foreground"
              >
                <option value={0}>Selecciona una categoría</option>
                {categories.map((cat) => (
                  <option key={cat.id} value={cat.id}>
                    {cat.name}
                  </option>
                ))}
              </select>
            </div>
            <Input
              placeholder="Descripción"
              value={formData.description}
              onChange={(e) => setFormData({ ...formData, description: e.target.value })}
              disabled={isLoading}
            />
            <div className="flex gap-2">
              <Button type="submit" disabled={isLoading || !formData.name || !formData.categoryId}>
                {isLoading ? "Guardando..." : editingId ? "Actualizar" : "Crear"}
              </Button>
              {editingId && (
                <Button type="button" variant="outline" onClick={handleCancel} disabled={isLoading}>
                  Cancelar
                </Button>
              )}
            </div>
          </form>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle>Productos ({products.length})</CardTitle>
        </CardHeader>
        <CardContent>
          {products.length === 0 ? (
            <p className="text-muted-foreground text-center py-4">No hay productos disponibles</p>
          ) : (
            <div className="space-y-3">
              {products.map((product) => (
                <div
                  key={product.id}
                  className="p-4 border rounded-lg flex justify-between items-start hover:bg-muted/50 transition"
                >
                  <div className="flex-1">
                    <h3 className="font-semibold">{product.name}</h3>
                    <p className="text-sm text-muted-foreground">{product.description}</p>
                    <div className="flex gap-4 mt-2 text-sm">
                      <span className="font-medium">Precio: ${product.price.toFixed(2)}</span>
                      <span>Stock: {product.stock}</span>
                      <span className="text-muted-foreground">{product.categoryName}</span>
                    </div>
                  </div>
                  <div className="flex gap-2">
                    <Button variant="outline" size="sm" onClick={() => handleEdit(product)} disabled={isLoading}>
                      <Edit2 className="w-4 h-4" />
                    </Button>
                    <Button
                      variant="destructive"
                      size="sm"
                      onClick={() => handleDelete(product.id)}
                      disabled={isLoading}
                    >
                      <Trash2 className="w-4 h-4" />
                    </Button>
                  </div>
                </div>
              ))}
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  )
}
