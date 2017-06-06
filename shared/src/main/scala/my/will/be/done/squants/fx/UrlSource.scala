package my.will.be.done.squants.fx

import fr.hmil.roshttp.HttpRequest

trait UrlSource extends HttpRequestSource {
  def sourceUrl: String
  override def httpRequest: HttpRequest = HttpRequest(sourceUrl)
}
