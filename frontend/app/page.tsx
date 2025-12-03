"use client"

import { useState } from "react"
import { CategoriesSection } from "@/components/categories-section"
import { ProductsSection } from "@/components/products-section"
import { CartsSection } from "@/components/carts-section"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Package, Tag, ShoppingCart } from "lucide-react"

export default function Home() {
  const [activeTab, setActiveTab] = useState("categories")

  return (
    <div className="min-h-screen bg-gradient-to-br from-background to-muted">
      <div className="max-w-7xl mx-auto px-4 py-8">
        <div className="mb-8">
          <h1 className="text-4xl font-bold text-foreground mb-2">E-Commerce Manager</h1>
          <p className="text-muted-foreground">Gestiona tus productos, categorías y carritos de compra</p>
        </div>

        <Tabs value={activeTab} onValueChange={setActiveTab} className="w-full">
          <TabsList className="grid w-full grid-cols-3 mb-8">
            <TabsTrigger value="categories" className="flex items-center gap-2">
              <Tag className="w-4 h-4" />
              Categorías
            </TabsTrigger>
            <TabsTrigger value="products" className="flex items-center gap-2">
              <Package className="w-4 h-4" />
              Productos
            </TabsTrigger>
            <TabsTrigger value="carts" className="flex items-center gap-2">
              <ShoppingCart className="w-4 h-4" />
              Carritos
            </TabsTrigger>
          </TabsList>

          <TabsContent value="categories">
            <CategoriesSection />
          </TabsContent>

          <TabsContent value="products">
            <ProductsSection />
          </TabsContent>

          <TabsContent value="carts">
            <CartsSection />
          </TabsContent>
        </Tabs>
      </div>
    </div>
  )
}
