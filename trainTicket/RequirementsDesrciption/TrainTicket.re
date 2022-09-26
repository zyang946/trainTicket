As a passenger, I want to buyTicket, so that I can travel at right time
{
Basic Flow {
	(User) 1. the Passenger shall come to Ticket Vending Machine to purchase tickets.
	(User) 2. the Passenger shall input destination.
	(System) 3. When starts a new query, the Ticket Vending Machine shall display all tickets based destination.
	(User) 4. the Passenger shall choose right time ticket and seat.
	(System) 5. When completes choosing, the Ticket Vending Machine shall generate a random ticket number and store order for passenger.
	(System) 6. Ticket Vending Machine shall display price according to information of ticket selected by user.
	(User) 7. the Passenger shall pay ticket within specified time.
	(System) 9. if completes paying, Ticket Vending Machine shall record ticket for passenger.
	}
}
As a passenger, I want to queryTicket, so that I can know all information about my ticket
{
	Basic Flow {
		(User) 1. the Passenger shall come to Ticket Vending Machine to check ticket information according to ticket number.
		(User) 2. the Passenger shall input ticket number.
		(System) 3. When ticket Number is input, Ticket Vending Machine shall display ticket information based on ticket number.
	}
}
As a passenger, I want to deleteTicket, so that I can cancel my ticket{
	Basic Flow {
		(User) 1. the passenger shall come to Ticket Vending Machine.
		(User) 2. the passenger shall input ticket number to cancel this Ticket.
		(System) 3. When compelete inputing, the Ticket Vending Machine shall cancel this ticket.
	}
}
As a passenger, I want to queryInformation, so that I can know more information {
	Basic Flow {
		(User) 1. the Passenger shall come to Ticket Vending Machine to purchase tickets.
		(User) 2. the Passenger shall input date and destination.
		(System) 3. When starts a new query, the Ticket Vending Machine shall display all tickets based on date and destination.
	}
}
As a Passenger, I want to updateTicket, so that I can modify my ticket {
	Basic Flow {
		(User) 1. the Passenger shall come to Ticket Vending Machine to modify information of ticket.
		(User) 2. the Passenger shall choose right ticket to replace his ticket.
		(User) 3. If price of new ticket is different from old, the Passenger shall pay difference.
		(System) 4. When compelet choosing, the Ticket Vending Machine shall change information of ticket.
	}
}
As a passenger, I want to createAccout, so that I can buy ticket {
	Basic Flow {
		(User) 1. the Passenger shall come to Ticket Vending Machine to create a new account.
		(User) 2. the Passenger shall input information include name and phoneNumber.
		(System) 3. When completes registering, the Ticket Vending Machine shall create a new accout for passenger.
	}
}
As a Administrator, I want to findAllOrder, so that I can fully know situation{
	Basic Flow {
		(User) 1. the Administrator shall enter Administrator interface to check all orders.
		(System) 2. the System shall show all orders.
	}	
}
As a Administrator, I want to findAllAccout, so that I can fully know situation{
	Basic Flow {
		(User) 1. the Administrator shall enter Administrator interface to check all accouts.
		(System) 2. the System shall show all accouts.
	}	
}
As a Staff, I want to createRoute, so that passengers can buy ticket{
	Basic Flow {
		(User) 1. the Staff shall input specific information about trip.
		(System) 2. the System shall generate a new trip on ticket machine based on information entered.
	}
}
As a Staff, I want to deleteRoute, so that it can allow passengers to avoid buying routes that break down{
	Basic Flow {
		(User) 1. the Staff shall choose route of failure to delete.
		(System) 2. the System shall delete this chosen route.
	}
}
As a Staff, I want to updateRoute, so that it can informing passengers of route information changes{
	Basic Flow {
		(User) 1. the Staff shall input new information about route.
		(System) 2. the System shall change information and show them to passenger.
	}
}

