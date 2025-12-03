"use client"

import type React from "react"

import { useState } from "react"
import useSWR from "swr"
import { type Category, categoryService } from "@/lib/api-services"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"

export function CategoriesSection() {
  const [name, setName] = useState("")
  const [isLoading, setIsLoading] = useState(false)
  const { data: categories = [], mutate } = useSWR("categories", () => categoryService.list(), {
    revalidateOnFocus: false,
  })

  const handleCreateCategory = async (e: React.FormEvent) => {
    e.preventDefault()
    if (!name.trim()) return

    setIsLoading(true)
    try {
      await categoryService.create({ name: name.trim() })
      setName("")
      await mutate()
    } catch (error) {
      console.error("Failed to create category:", error)
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <div className="space-y-6">
      <Card>
        <CardHeader>
          <CardTitle>Crear Nueva Categoría</CardTitle>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleCreateCategory} className="flex gap-2">
            <Input
              placeholder="Nombre de la categoría"
              value={name}
              onChange={(e) => setName(e.target.value)}
              disabled={isLoading}
            />
            <Button type="submit" disabled={isLoading || !name.trim()}>
              {isLoading ? "Creando..." : "Crear"}
            </Button>
          </form>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle>Categorías ({categories.length})</CardTitle>
        </CardHeader>
        <CardContent>
          {categories.length === 0 ? (
            <p className="text-muted-foreground text-center py-4">No hay categorías disponibles</p>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-3">
              {categories.map((category: Category) => (
                <div key={category.id} className="p-3 border rounded-lg">
                  <p className="font-semibold text-sm">{category.name}</p>
                  <p className="text-xs text-muted-foreground">ID: {category.id}</p>
                </div>
              ))}
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  )
}
