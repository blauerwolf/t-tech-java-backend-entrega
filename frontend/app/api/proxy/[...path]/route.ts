import { type NextRequest, NextResponse } from "next/server"

const EXTERNAL_API_URL = process.env.EXTERNAL_API_URL || "http://localhost:8080"

export async function GET(request: NextRequest, { params }: { params: Promise<{ path: string[] }> }) {
  try {
    const { path } = await params
    const pathname = `/${path.join("/")}`
    const searchParams = request.nextUrl.searchParams
    const queryString = searchParams.toString()
    const url = `${EXTERNAL_API_URL}${pathname}${queryString ? `?${queryString}` : ""}`

    console.log("[v0] GET request to:", url)
    const response = await fetch(url, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    })

    const data = await response.json()
    return NextResponse.json(data, { status: response.status })
  } catch (error) {
    console.error("[v0] GET error:", error)
    return NextResponse.json({ error: String(error) }, { status: 500 })
  }
}

export async function POST(request: NextRequest, { params }: { params: Promise<{ path: string[] }> }) {
  try {
    const { path } = await params
    const pathname = `/${path.join("/")}`
    const searchParams = request.nextUrl.searchParams
    const queryString = searchParams.toString()
    const url = `${EXTERNAL_API_URL}${pathname}${queryString ? `?${queryString}` : ""}`
    const body = await request.json().catch(() => null)

    console.log("[v0] POST request to:", url, "body:", body)
    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: body ? JSON.stringify(body) : undefined,
    })

    const data = await response.json()
    return NextResponse.json(data, { status: response.status })
  } catch (error) {
    console.error("[v0] POST error:", error)
    return NextResponse.json({ error: String(error) }, { status: 500 })
  }
}

export async function PUT(request: NextRequest, { params }: { params: Promise<{ path: string[] }> }) {
  try {
    const { path } = await params
    const pathname = `/${path.join("/")}`
    const searchParams = request.nextUrl.searchParams
    const queryString = searchParams.toString()
    const url = `${EXTERNAL_API_URL}${pathname}${queryString ? `?${queryString}` : ""}`
    const body = await request.json().catch(() => null)

    console.log("[v0] PUT request to:", url, "body:", body)
    const response = await fetch(url, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: body ? JSON.stringify(body) : undefined,
    })

    const data = await response.json()
    return NextResponse.json(data, { status: response.status })
  } catch (error) {
    console.error("[v0] PUT error:", error)
    return NextResponse.json({ error: String(error) }, { status: 500 })
  }
}

export async function DELETE(request: NextRequest, { params }: { params: Promise<{ path: string[] }> }) {
  try {
    const { path } = await params
    const pathname = `/${path.join("/")}`
    const searchParams = request.nextUrl.searchParams
    const queryString = searchParams.toString()
    const url = `${EXTERNAL_API_URL}${pathname}${queryString ? `?${queryString}` : ""}`

    console.log("[v0] DELETE request to:", url)
    const response = await fetch(url, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    })

    // Handle responses that may not be JSON (like "OK")
    const contentType = response.headers.get("content-type")
    let data
    if (contentType?.includes("application/json")) {
      data = await response.json()
    } else {
      const text = await response.text()
      data = text ? { message: text } : { success: true }
    }

    return NextResponse.json(data, { status: response.status })
  } catch (error) {
    console.error("[v0] DELETE error:", error)
    return NextResponse.json({ error: String(error) }, { status: 500 })
  }
}
