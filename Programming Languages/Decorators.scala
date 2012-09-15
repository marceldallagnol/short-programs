object Decorators {
    
  object profile { 
    
    private var cm: Map[String, Int] =  Map().withDefault(_ => 0)

    def count(name: String) = 
      cm(name)
   
    def reset(name: String) = 
      cm += name -> 0

    def apply[A, B](name: String)(f: A => B) = new Function1[A, B] {
      def apply(x: A) = {
        val n = cm(name)
        cm += name -> (1 + n)
        f(x)
      }
    }
  }
 
  object trace {
    
      private var indLevel: Int =  0
    def apply[A, B](name: String)(f: A => B) : Function1[A, B] = new Function1[A, B] {
      def apply (x: A): B = {
         for(i <- 1 to indLevel) { print("| ") }
         println(",- " + name + "(" + x.toString + ")")
         val origInd = indLevel
         indLevel += 1
         val result = try { f(x) }
         catch {
            case e => indLevel -= 1
                      throw e
         }
         indLevel -= 1
         for(i <- 1 to origInd) { print("| ") }
         println("`- " + result.toString)
         result      
      }   
    }
  } 
  
  
  object memo {
    def apply[A, B](f: A => B) : Function1[A, B] = new Function1[A, B] {
      private var cache: Map[A,Either[B,Throwable]] =  Map()
      def apply (x: A): B = {
         cache.get(x) match {
            case Some(Left(result))       => result
            case Some(Right(exception))   => throw exception
            case None                     => val result = try { f(x) }
                                             catch {
                                                case e => cache += (x -> Right(e))
                                                          throw e
                                             }
                                             cache += (x -> Left(result))
                                             result
         }
      }
    }
  }

}


