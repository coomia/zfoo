import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import '@polymer/iron-input/iron-input.js';

// Define the class for a new element called custom-element
class CustomElement extends PolymerElement {
    static get properties() {
        return {
            owner: {
                type: String,
                value: 'Daniel'
            }
        };
    }


    // The following example uses two-way binding: binding the value of a custom input element (iron-input) to the element's owner property, so it's updated as the user types.
    static get template() {
        return html`
        <!-- bind to the 'owner' property -->
        <p>This is <b>[[owner]]</b>'s name-tag element.</p>

        <!-- iron-input exposes a two-way bindable input value -->
        <iron-input bind-value="{{owner}}">
         <!-- TODO: Edit the placeholder text to see two-way data binding at work. -->
        <input is="iron-input" placeholder="Your name here..."></iron-input>
        `;
    }
}

// Register the new element
customElements.define('custom-element', CustomElement);