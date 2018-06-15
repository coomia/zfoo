import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import '@polymer/iron-input/iron-input.js';

class CustomElement extends PolymerElement {

    static get is() {
        return "custom-element";
    }

    // configure the owner property
    static get properties() {
        return {
            foo: {
                type: String,
                value: 'World'
            }
        };
    }

    static get template() {
        return html`
        
        <!-- bind to the "owner" property -->
        <p>Hello <b>[[foo]]</b>!</p>
        <!-- iron-input exposes a two-way bindable input value -->
        <iron-input bind-value="{{foo}}">
            <input is="iron-input" placeholder="Your name here...">
        </iron-input>
        
        `;
    }
}

customElements.define(CustomElement.is, CustomElement);