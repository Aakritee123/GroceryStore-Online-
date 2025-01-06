// Handle payment method change to show/hide Stripe form
document.getElementById("paymentMethod").addEventListener("change", function () {
    var stripePayment = document.getElementById("stripe-payment");
    if (this.value === "Stripe") {
        stripePayment.style.display = "block";
    } else {
        stripePayment.style.display = "none";
    }
});

// Initialize Stripe with your publishable key
var stripe = Stripe('pk_test_51Qeb26G0i94Adaik0kHEMtzNdwkEW9TFAv8cX0kE1T4OoCzYzJiHNMideUSRSYyFMUvdpPedI2tSypnzhqm90ASs00flR9c5Qv');  // Replace with your actual key
var elements = stripe.elements();
var card = elements.create('card'); // Create the card input field
card.mount('#card-element'); // Mount it to the element with ID 'card-element'

// Handle form submission
var form = document.getElementById('checkout-form');
form.addEventListener('submit', function (event) {
    event.preventDefault(); // Prevent default form submission

    // If the payment method is Stripe, create a token
    var paymentMethod = document.getElementById('paymentMethod').value;
    if (paymentMethod === "Stripe") {
        stripe.createToken(card).then(function (result) {
            if (result.error) {
                // Display error message
                var errorElement = document.getElementById('card-errors');
                errorElement.textContent = result.error.message;
            } else {
                // Append the Stripe Token to the form
                var tokenInput = document.createElement('input');
                tokenInput.setAttribute('type', 'hidden');
                tokenInput.setAttribute('name', 'stripeToken');
                tokenInput.setAttribute('value', result.token.id);
                form.appendChild(tokenInput);

                // Submit the form with the token
                form.submit();
            }
        });
    } else {
        // For other payment methods, just submit the form
        form.submit();
    }
});
