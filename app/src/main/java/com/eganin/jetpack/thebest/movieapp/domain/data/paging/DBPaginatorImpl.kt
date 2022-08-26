package com.eganin.jetpack.thebest.movieapp.domain.data.paging

class DBPaginatorImpl<Item>(
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend () -> List<Item>,
    private inline val onSuccess: suspend (items: List<Item>) -> Unit
) : DBPaginator<Item> {

    private var isMakingRequest = false

    override suspend fun loadItems() {
        if (isMakingRequest) return
        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest()
        isMakingRequest = false
        onSuccess(result)
        onLoadUpdated(false)
    }
}