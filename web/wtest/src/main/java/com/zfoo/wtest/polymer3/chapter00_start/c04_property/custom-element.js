import {PolymerElement} from '@polymer/polymer/polymer-element.js';

// Define the class for a new element called custom-element
class CustomElement extends PolymerElement {
    static get properties() {
        return {
            // Configure owner property
            owner: {
                type: String,
                value: 'Daniel',
            }
        };
    }

    static get template() {
        return html`
        <!-- bind to the "owner" property -->
        This is <b>[[owner]]</b>'s name-tag element.
        `;
    }
}

// Register the new element
customElements.define('custom-element', CustomElement);