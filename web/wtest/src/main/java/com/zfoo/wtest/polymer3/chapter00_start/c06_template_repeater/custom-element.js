import {PolymerElement, html} from '@polymer/polymer/polymer-element.js'
import '@polymer/polymer/lib/elements/dom-repeat.js'

class CustomElement extends PolymerElement {

    static get is() {
        return "custom-element";
    }

    constructor() {
        super();

        this.employees = [
            {first: 'Bob', last: 'Li'},
            {first: 'Ayesha', last: 'Johnson'},
            {first: 'Fatma', last: 'Kumari'},
            {first: 'Tony', last: 'Mori'}
        ];
    }


    // Using <dom-repeat> for template repeating
    // The template repeater (dom-repeat) is a specialized template that binds to an array.
    // It creates one instance of the template's contents for each item in the array.
    static get template() {
        return html`
        <div> Employee list: </div>
        <p></p>
        <template is="dom-repeat" items="{{employees}}">
            <div># [[index]]</div>
            <div>First name: <span>{{item.first}}</span></div>
            <div>Last name: <span>{{item.last}}</span></div>
            <p></p>
        </template>
        `;
    }
}

customElements.define(CustomElement.is, CustomElement);