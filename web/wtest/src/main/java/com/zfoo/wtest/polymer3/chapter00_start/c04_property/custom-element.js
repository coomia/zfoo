import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';

class CustomElement extends PolymerElement {

    static get is() {
        return "custom-element";
    }

    // configure the owner property
    static get properties() {
        return {
            foo: String
        };
    }

    static get template() {
        return html`
        
        <!-- bind to the "owner" property -->
        <p>Hello <b>[[foo]]</b>!</p>
        
        `;
    }
}

customElements.define(CustomElement.is, CustomElement);