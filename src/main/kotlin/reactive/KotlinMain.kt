package reactive

import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.support.beans

fun main(args: Array<String>) {
    SpringApplicationBuilder()
            .initializers(beans {
                bean {
                    Bar()
                }

                bean {
                    val refToBar = ref<Bar>()
                    Foo(refToBar)
                }
            })
            .sources(SimpleSpringBootApp::class.java)
            .run(*args)
}

class Bar

class Foo(val bar: Bar)