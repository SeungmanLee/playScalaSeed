package controllers

import javax.inject.Inject

import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import play.api.mvc.Results.Ok
import play.mvc.Controller
import models.Product

/**
  * Created by smlee on 17. 10. 2.
  */
class Products @Inject() (val messagesApi: MessagesApi)
    extends Controller with I18nSupport {


  def list = Action { implicit request =>
    val products = Product.findAll
    Ok(views.html.products.list(products))
  }
}
