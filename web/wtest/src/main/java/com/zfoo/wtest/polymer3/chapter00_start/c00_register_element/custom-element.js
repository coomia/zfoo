import {PolymerElement} from '../../node_modules/@polymer/polymer/polymer-element.js';

// To register a new element：
// Define the class for a new element called custom-element
class CustomElement extends PolymerElement {

    // The custom element's name must start with an ASCII letter and contain a dash (-).
    static get is() {
        return "custom-element";
    }

    constructor() {
        super();
        this.textContent = "I'm a custom-element.";
        console.log(this.tagName);
    }
}

// Registering an element associates an element name with a class, so you can add properties and methods to your custom element.
customElements.define(CustomElement.is, CustomElement);