package com.smallain.adt

object OptionModule {
	
	sealed trait Option[+T] {
		def get: T
		
		def isEmpty: Boolean
		
		def getOrElse[B >: T](default: B): B
		
		//Functor
		def map[R](f: T => R): Option[R]
		
		//Applicative
		def <%>[R](optFunc: Option[T => R]): Option[R]
		
		//Monad
		def flatMap[R](f: T => Option[R]): Option[R]
	}
	
	case class Just[T](value: T) extends Option[T] {
		override def get: T = value
		
		override def isEmpty: Boolean = false
		
		override def getOrElse[B >: T](default: B): B = value
		
		override def map[R](f: T => R): Option[R] = Just(f(value))
		
		override def <%>[R](optFunc: Option[T => R]): Option[R] = {
			if (optFunc.isEmpty) NotExist else Just(optFunc.get(value))
		}
		
		override def flatMap[R](f: T => Option[R]): Option[R] = {
			val optResult = f(value)
			if (optResult.isEmpty) NotExist else optResult
		}
	}
	
	case object NotExist extends Option[Nothing] {
		override def get: Nothing = ???
		
		override def isEmpty: Boolean = true
		
		override def getOrElse[B >: Nothing](default: B): B = default
		
		override def map[R](f: Nothing => R): Option[R] = NotExist
		
		override def <%>[R](optFunc: Option[Nothing => R]): Option[R] = NotExist
		
		override def flatMap[R](f: Nothing => Option[R]): Option[R] = NotExist
	}
	
}
