package models

/**
  * Created by smlee on 17. 10. 2.
  */
case class Product(ean: Long, name: String, description: String)

object Product {
  var products = Set( Product(5010255079763L, "AAAA", "11111"),
    Product(5018206244666L, "BBBB", "22222"),
    Product(5010377823886L, "CCCC", "33333"),
    Product(5014546362534L, "DDDD", "44444"),
    Product(5018392977232L, "EEEE", "55555")
  )

  def findAll = products.toList.sortBy(_.ean)

  def findByEan(ean: Long) = products.find(_.ean == ean)

  def add(product: Product): Unit = {
    products += product
  }
}