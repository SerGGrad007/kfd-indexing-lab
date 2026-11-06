package lab2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotSame
import org.junit.jupiter.api.Test

class ProductSelectionTest {
    @Test
    fun selectsAvailableProductsInRecommendationOrder() {
        val keyboard = Product(1, "Keyboard", true)
        val mouse = Product(2, "Mouse", false)
        val monitor = Product(3, "Monitor", true)
        val webcam = Product(4, "Webcam", true)
        val products = listOf(keyboard, mouse, monitor, webcam)

        val result = selectProducts(
            products,
            listOf(3L, 2L, 3L, 99L, 1L, 4L),
            listOf(1L)
        )

        assertEquals(listOf(monitor, webcam), result)
    }

    @Test
    fun returnsEmptyListWhenThereAreNoRecommendations() {
        val products = listOf(Product(1, "Keyboard", true))

        val result = selectProducts(products, emptyList(), emptyList())

        assertEquals(emptyList<Product>(), result)
    }

    @Test
    fun repeatingRecommendationsAndExcludesProducts() {
        val hyperX = Product(1, "HyperX", true)
        val logiTech = Product(2, "LogiTech", true)
        val ps5 = Product(3, "PS5", true)
        val xbox = Product(4, "Xbox", true)
        val ybox = Product(5, "Ybox", true)

        val products = listOf(hyperX, logiTech, ps5, xbox, ybox)

        val result = selectProducts(
            products,
            listOf(3L, 2L, 3L, 99L, 1L, 4L, 5L),
            listOf(1L, 1L, 3L, 3L)
        )
        assertEquals(listOf(logiTech, xbox, ybox), result)
    }

    @Test
    fun unavailableExcludedUnknownProducts() {
        val hyperX = Product(1, "HyperX", true)
        val logiTech = Product(2, "LogiTech", false)
        val ps5 = Product(3, "PS5", true)
        val xbox = Product(4, "Xbox", true)
        val ybox = Product(5, "Ybox", false)
        val zbox = Product(6, "Zbox", true)

        val products = listOf(hyperX, logiTech, ps5, xbox, ybox, zbox)

        val result = selectProducts(
            products,
            listOf(1L, 2L, 3L, 67L, 1L, 4L, 5L),
            listOf(1L, 6L, 2L, 2L)
        )
        assertEquals(listOf(ps5, xbox), result)
    }

    @Test
    fun allIsEmpty(){
        val result = selectProducts(emptyList(),
            emptyList(),
            emptyList()
        )
        assertEquals(emptyList<Product>(), result)
    }

    @Test
    fun nothingFits() {
        val hyperX = Product(1, "HyperX", true)
        val logiTech = Product(2, "LogiTech", false)
        val ps5 = Product(3, "PS5", true)
        val xbox = Product(4, "Xbox", true)
        val ybox = Product(5, "Ybox", false)
        val zbox = Product(6, "Zbox", true)

        val products = listOf(hyperX, logiTech, ps5, xbox, ybox, zbox)

        val result = selectProducts(
            products,
            listOf(2L, 33L, 67L, 4L, 5L, 6L),
            listOf(1L, 6L, 2L, 2L, 4L)
        )
        assertEquals(emptyList<Product>(), result)
    }

    @Test
    fun inputDoesNotChange() {
        val hyperX = Product(1, "HyperX", true)
        val logiTech = Product(2, "LogiTech", false)
        val ps5 = Product(3, "PS5", true)
        val xbox = Product(4, "Xbox", true)
        val ybox = Product(5, "Ybox", false)
        val zbox = Product(6, "Zbox", true)

        val products = mutableListOf(hyperX, logiTech, ps5, xbox, ybox, zbox)
        val recommended = mutableListOf(1L, 2L, 3L, 67L, 1L, 4L, 5L)
        val excluded = mutableListOf(1L, 6L, 2L, 2L)

        val productsBefore = products.toList()
        val recommendedBefore = recommended.toList()
        val excludedBefore = excluded.toList()

        selectProducts(
            products,
            recommended,
            excluded
        )

        assertEquals(productsBefore, products)
        assertEquals(recommendedBefore, recommended)
        assertEquals(excludedBefore, excluded)

    }

    @Test
    fun repeatingCallsAreIndependent() {
        val keyboard = Product(1, "Keyboard", true)
        val mouse = Product(2, "Mouse", false)
        val monitor = Product(3, "Monitor", true)
        val webcam = Product(4, "Webcam", true)
        val products = listOf(keyboard, mouse, monitor, webcam)

        val first = selectProducts(
            products,
            listOf(3L, 2L, 3L, 99L, 1L, 4L),
            listOf(1L)
        )
        val second = selectProducts(
            products,
            listOf(2L, 3L, 67L, 1L, 4L, 5L),
            listOf(3L, 6L, 2L, 2L, 2L)
        )
        val again = selectProducts(
            products,
            listOf(3L, 2L, 3L, 99L, 1L, 4L),
            listOf(1L)
        )
        assertEquals(listOf(monitor, webcam), first)
        assertEquals(listOf(keyboard, webcam), second)
        assertEquals(first, again)
        assertNotSame(first, again)
    }
}
