package com.shyam.AirlineReservationSystem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

@SpringBootApplication
class AirlineReservationSystemApplication{
	@Bean
	fun scheduledExecutorService(): ScheduledExecutorService = Executors.newScheduledThreadPool(5)
}

fun main(args: Array<String>) {
	runApplication<AirlineReservationSystemApplication>(*args)
}
