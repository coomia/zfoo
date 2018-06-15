import {PolymerElement, html} from '../../node_modules/@polymer/polymer/polymer-element.js';


// Use data binding:
// Of course, it's not enough to have static shadow DOM. You usually want to have your element update its shadow DOM dynamically.
// Data binding is a great way to quickly propagate changes in your element and reduce boilerplate code.
// You can bind properties in your component using the "double-mustache" syntax ({{}}).
// The {{}} is replaced by the value of the property referenced between the brackets.
class CustomElement extends PolymerElement {

    static get is() {
        return "custom-element";
    }

    constructor() {
        super();
        /* set this element's owner property */
        this.foo = "World";
    }

    static get template() {
        return html`
        <!-- bind to the "owner" property -->
        Hello <b>{{foo}}</b>!
        `;
    }
}

// Register the new element
customElements.define(CustomElement.is, CustomElement);
