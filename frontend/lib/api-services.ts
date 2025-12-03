import { apiClient } from "./api-client"

// Types based on Swagger spec
export interface Category {
  id?: number
  name: string
}

export interface ProductRequestDTO {
  name: string
  image: string
  price: number
  stock: number
  categoryId: number
  description: string
}

export interface ProductResponseDTO extends ProductRequestDTO {
  id: number
  categoryName: string
}

export interface CartItemResponseDTO {
  id: number
  productId: number
  productName: string
  quantity: number
  price: number
  subtotal: number
}

export interface CartResponseDTO {
  id: number
  items: CartItemResponseDTO[]
  total: number
}

export interface CartItemRequestDTO {
  productId: number
  quantity: number
}

// Category Services
export const categoryService = {
  async list(): Promise<Category[]> {
    return apiClient.get<Category[]>("/categorias")
  },

  async create(category: Category): Promise<Category> {
    return apiClient.post<Category>("/categorias", category)
  },
}

// Product Services
export const productService = {
  async list(params?: {
    page?: number
    size?: number
    sortBy?: string
    direction?: string
    categoryId?: number
    name?: string
    minPrice?: number
    maxPrice?: number
  }): Promise<any> {
    const query = new URLSearchParams()
    if (params) {
      Object.entries(params).forEach(([key, value]) => {
        if (value !== undefined) {
          query.append(key, String(value))
        }
      })
    }
    return apiClient.get<any>(`/products?${query.toString()}`)
  },

  async getById(id: number): Promise<ProductResponseDTO> {
    return apiClient.get<ProductResponseDTO>(`/products/${id}`)
  },

  async create(product: ProductRequestDTO): Promise<ProductResponseDTO> {
    return apiClient.post<ProductResponseDTO>("/products", product)
  },

  async update(id: number, product: ProductRequestDTO): Promise<ProductResponseDTO> {
    return apiClient.put<ProductResponseDTO>(`/products/${id}`, product)
  },

  async delete(id: number): Promise<ProductResponseDTO> {
    return apiClient.delete<ProductResponseDTO>(`/products/${id}`)
  },

  async search(queryName: string): Promise<ProductResponseDTO[]> {
    return apiClient.get<ProductResponseDTO[]>(`/products/search?queryName=${encodeURIComponent(queryName)}`)
  },
}

// Cart Services
export const cartService = {
  async create(): Promise<CartResponseDTO> {
    return apiClient.post<CartResponseDTO>("/carts")
  },

  async getById(id: number): Promise<CartResponseDTO> {
    return apiClient.get<CartResponseDTO>(`/carts/${id}`)
  },

  async addItem(cartId: number, item: CartItemRequestDTO): Promise<CartResponseDTO> {
    return apiClient.post<CartResponseDTO>(`/carts/${cartId}/items`, item)
  },

  async updateItem(cartId: number, itemId: number, quantity: number): Promise<CartResponseDTO> {
    return apiClient.put<CartResponseDTO>(`/carts/${cartId}/items/${itemId}?quantity=${quantity}`)
  },

  async deleteItem(cartId: number, itemId: number): Promise<CartResponseDTO> {
    return apiClient.delete<CartResponseDTO>(`/carts/${cartId}/items/${itemId}`)
  },

  async delete(cartId: number): Promise<void> {
    return apiClient.delete<void>(`/carts/${cartId}`)
  },
}
