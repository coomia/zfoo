import {PolymerElement} from '@polymer/polymer/polymer-element.js';

// Define the class for a new element called custom-element
class CustomElement extends PolymerElement {
    constructor() {
        super();

        /* set this element's owner property */
        this.owner = 'Daniel';
    }

    static get template() {
        return html`
        <!-- bind to the "owner" property -->
        This is <b>{{owner}}</b>'s name-tag element.
        `;
    }
}

// Register the new element
customElements.define('custom-element', CustomElement);

/* TODO:
  * - Try editing the value of the `owner` property.
  * - Try adding another property and binding it in your component.
  * Hint: Add the following property definition to the constructor: `this.propertyName = "Property contents";` and add `{{propertyName}}` to the element’s shadow DOM.
  */
