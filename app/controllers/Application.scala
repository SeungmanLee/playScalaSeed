package controllers

import play.api.mvc.Action
import play.mvc.Controller
import play.api.mvc.Results._

/**
  * Created by smlee on 17. 10. 2.
  */
class Application extends Controller {

  def index = Action {
    Redirect(routes.Products.list())
  }

}
