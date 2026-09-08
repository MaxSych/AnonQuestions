document.addEventListener('DOMContentLoaded', () => {
    const card = document.querySelector('.glass-card');

    // Check to prevent errors if the form is not present on the page
    if (!card) return;

    document.addEventListener('mousemove', (e) => {
        const { clientX, clientY } = e;
        const { left, top, width, height } = card.getBoundingClientRect();

        // Find the center of the form
        const centerX = left + width / 2;
        const centerY = top + height / 2;


        const tiltX = (clientX - centerX) / (window.innerWidth / 2);
        const tiltY = (clientY - centerY) / (window.innerHeight / 2);


        gsap.to(card, {
            duration: 0.6,
            rotationY: tiltX * 12, // Horizontal tilt angle
            rotationX: -tiltY * 12, // Vertical tilt angle
            ease: "power2.out",
            transformPerspective: 1000
        });
    });

    // Reset the form to its original position when the mouse leaves the window
    document.addEventListener('mouseleave', () => {
        gsap.to(card, {
            duration: 1.2,
            rotationY: 0,
            rotationX: 0,
            ease: "elastic.out(1, 0.6)"
        });
    });
});