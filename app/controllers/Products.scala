package controllers

import javax.inject.Inject

import play.api.i18n.{Messages}
import play.api.mvc._
import models.Product

/**
  * Created by smlee on 17. 10. 2.
  */
class Products @Inject() (cc:ControllerComponents)
    extends AbstractController(cc) with play.api.i18n.I18nSupport {

  import play.api.data.Form
  import play.api.data.Forms._


  def list = Action { implicit request =>
    val products = Product.findAll
    Ok(views.html.products.list(products))
  }

  def show(ean: Long) = Action { implicit request =>

    Product.findByEan(ean).map { product =>
      Ok(views.html.products.details(product))
    }.getOrElse(NotFound)
  }

  private val productForm: Form[Product] = Form(
    mapping(
      "ean" -> longNumber.verifying("validation.ean.duplicate", eanVerify(_)),
      "name" -> nonEmptyText,
      "description" -> nonEmptyText
      )(Product.apply)(Product.unapply)
  )

  private def eanVerify( ean: Long) = {
    val ret = Product.findByEan(ean)
    ret.isEmpty
  }


  def save = Action { implicit request =>
    val newProductForm = productForm.bindFromRequest()

    newProductForm.fold(
      hasErrors = { form =>
        Redirect(routes.Products.newProduct()).
          flashing(Flash(form.data) +
            ("error" -> Messages("validation.errors")))
      },

      success = { newProduct =>
        Product.add(newProduct)
        val message = Messages("products.new.success", newProduct.name)
        Redirect(routes.Products.show(newProduct.ean)).
          flashing("success" -> message)
      }
    )
  }

  def newProduct = Action { implicit request =>
    val form = if (request2flash.get("error").isDefined)
                  productForm.bind(request2flash.data)
                else
                  productForm

    Ok(views.html.products.editProduct(form))
  }

}
