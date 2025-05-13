const stripe = Stripe('pk_test_51RO9DFR8fGYOKVL8ZlHonfG91vf9ErrChcrKBwHxfQhg0MfWK8HYii3lyzDhJRf1fWZB5qwC9yp1cuI321SO7rFx00exnioJZH');
const paymentButton = document.querySelector('#paymentButton');

paymentButton.addEventListener('click', () => {
	stripe.redirectToCheckout({
		sessionId: sessionId
	})
});
