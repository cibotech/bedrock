import org.scalajs.core.tools.io._
import org.scalajs.jsenv.phantomjs.PhantomJSEnv

// https://github.com/scala-js/scala-js/issues/1555
class PhantomJS2Env(jettyClassLoader: ClassLoader)
    extends PhantomJSEnv(
      config = PhantomJSEnv
        .Config()
        .withArgs(List.empty)
        .withEnv(Map.empty)
        .withAutoExit(true)
        .withExecutable("phantomjs")
        .withJettyClassLoader(jettyClassLoader)
    ) {

  override protected def vmName: String = "PhantomJS2"

  private val consoleNuker = new MemVirtualJSFile("consoleNuker.js")
    .withContent("console.error = console.log;")

  override protected def customInitFiles(): Seq[VirtualJSFile] =
    super.customInitFiles() :+ consoleNuker
}
