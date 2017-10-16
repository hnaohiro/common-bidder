package logic

import java.net.InetSocketAddress

import com.twitter.finagle.Thrift
import com.twitter.server.TwitterServer
import com.twitter.util.Await
import logic.service.LogicService

object Server extends TwitterServer {

  val addr = flag("bind", new InetSocketAddress(9090), "bind address")

  def main() {
    log.info("start logic server")
    val server = Thrift.server.serveIface(addr(), new LogicService(log))
    onExit { server.close() }
    Await.ready(server)
  }
}