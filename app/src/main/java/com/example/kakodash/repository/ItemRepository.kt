package com.example.kakodash.repository
import com.example.kakodash.model.Item
import com.example.kakodash.network.ApiService
import retrofit2.Response
class ItemRepository(private val api: ApiService) {
    suspend fun getAll(): Response<List<Item>> = api.getItems()
    suspend fun get(id: Int): Response<Item> = api.getItem(id)
    suspend fun create(item: Item): Response<Item> = api.createItem(item)
    suspend fun update(id: Int, item: Item): Response<Item> = api.updateItem(id, item)
    suspend fun delete(id: Int): Response<Unit> = api.deleteItem(id)
}