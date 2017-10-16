package bid

import java.net.InetSocketAddress

import bid.filter.RequestFilter
import bid.service.BidService
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Http, Service, Thrift}
import com.twitter.server.TwitterServer
import com.twitter.util.Await
import common.LogicService

object Server extends TwitterServer {

  val addr = flag("bind", new InetSocketAddress(8080), "bind address")
  val logicDest = flag("logic", "localhost:9090", "logic destination")

  def main() {
    log.info(s"logic: ${logicDest()}")

    val logicServiceClient: LogicService.ServiceIface =
      Thrift.client.newServiceIface[LogicService.ServiceIface](logicDest(), "thrift_client")

    val requestFilter = new RequestFilter(log)
    val bidService = new BidService(log, logicServiceClient)
    val service: Service[Request, Response] = requestFilter andThen bidService

    val server = Http.serve(addr(), service)
    onExit { server.close() }
    Await.ready(server)
  }
}