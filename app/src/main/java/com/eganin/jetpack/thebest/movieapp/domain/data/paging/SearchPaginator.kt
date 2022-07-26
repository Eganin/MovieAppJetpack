package com.eganin.jetpack.thebest.movieapp.domain.data.paging

class SearchPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key , query:String) -> List<Item>,
    private inline val getNextKey: suspend (List<Item>) -> Key,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
) : Paginator<Key, Item> {

    private var currentKey = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItems(query : String) {
        if (isMakingRequest) return
        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest(currentKey , query)
        isMakingRequest = false
        currentKey = getNextKey(result)
        onSuccess(result, currentKey)
        onLoadUpdated(false)
    }

    override fun reset() {
        currentKey = initialKey
    }
}