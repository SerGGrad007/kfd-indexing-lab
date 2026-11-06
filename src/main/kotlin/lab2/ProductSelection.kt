package lab2

fun selectProducts(
    products: List<Product>,
    recommendedIds: List<Long>,
    excludedIds: List<Long>
): List<Product> {
    val catalog = products.associateBy { it.id }
    val excluded = excludedIds.toHashSet()
    val seen = HashSet<Long>()
    val result = ArrayList<Product>()

    for (id in recommendedIds) {
        if (id in excluded) continue
        val product = catalog[id] ?: continue
        if (product.available && seen.add(id)){
            result.add(product)
        }
    }
    return result
}
