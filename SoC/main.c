#include <avr/io.h>
#include <util/delay.h>
#include <avr/interrupt.h>
#define F_CPU 8000000UL
#define FOSC 8000000UL
#define BAUD 9600
#define MYUBRR ( FOSC  + BAUD * 8UL  ) / (16UL * BAUD) -1

#define LED_1 (1<<PB1)
#define LED_2 (1<<PB2)
#define LED_3 (1<<PB3)
#define LED_4 (1<<PB4)


#define LED1_ON  PORTB &= ~LED_1
#define LED1_OFF PORTB |= LED_1

#define LED2_ON  PORTB &= ~LED_2
#define LED2_OFF PORTB |= LED_2

#define LED3_ON  PORTB &= ~LED_3
#define LED3_OFF PORTB |= LED_3

#define LED4_ON  PORTB &= ~LED_4
#define LED4_OFF PORTB |= LED_4

volatile int i=0,no=0,on=1,off=0;
char c[1][10],k;


static char wiadomosc;

void USART_Init( unsigned int ubrr)
{

	UBRRH = (unsigned char)(ubrr>>8);
	UBRRL = (unsigned char)ubrr;

	UCSRB = (1<<RXEN)|(1<<TXEN)|(1<<RXC);

	UCSRC = (1<<URSEL)|(1<<USBS)|(3<<UCSZ0);
}
void USART_Transmit( unsigned char data )
{

	while ( !( UCSRA & (1<<UDRE)) )
	;

	UDR = data;
}

unsigned char USART_Receive( void )
{

	while ( !(UCSRA & (1<<RXC)) )
	;

	return UDR;
}


int main (void)
{

	//DDRB |= LED_1 | LED_2 | LED_3 | LED_4;
	//PORTB |= LED_1 | LED_2 | LED_3 | LED_4;
	DDRB|=(1<<1)|(1<<2)|(1<<3)|(1<<4)|(1<<5);
	PORTB|=(~(1<<1))|(1<<2)|(1<<3)|(1<<4)|(1<<5);

	USART_Init(MYUBRR);
	sei();
	while(1) {
		if(wiadomosc)
		{
			USART_Transmit(wiadomosc);
		}
		wiadomosc = 0;
	}
}

ISR(USART_RXC_vect)
{
	wiadomosc=UDR;
	switch(wiadomosc)
	{
		case '1':
			PORTB|=(1<<1);

		break;
		case '2':
			PORTB&=~(1<<1);
		break;
		case '3':
			PORTB|=(1<<2);
		break;

		case '4':
			PORTB&=~(1<<2);
		break;

		case '5':
			PORTB|=(1<<3);
		break;
		case '6':
			PORTB&=~(1<<3);
		break;

		case '7':
			PORTB|=(1<<4);
		break;

		case '8':
			PORTB&=~(1<<4);
		break;
	}
}
