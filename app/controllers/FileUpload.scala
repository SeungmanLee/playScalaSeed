package controllers

import java.io.File
import javax.inject.Inject

import play.api.mvc.{AbstractController, Action, ControllerComponents}

/**
  * Created by smlee on 17. 10. 9.
  */
class FileUpload @Inject() (cc:ControllerComponents)
  extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def index() = Action{ implicit request =>
    Ok(views.html.fileupload.fileupload())
  }

  def upload() = Action(parse.multipartFormData) { request =>
    request.body.file("image").map { file =>
      file.ref.moveTo(new File("/tmp/image.png"))
      Ok("Retrieved file %s" format file.filename)
    }.getOrElse(BadRequest("File missing!"))
  }
}
