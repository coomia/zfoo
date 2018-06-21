import { PolymerElement, html } from '@polymer/polymer/polymer-element.js';
import './custom-icon-toggle.js';

class CustomElement extends PolymerElement {
    static get is() {
        return "custom-element"
    }

    message(fav) {
        if (fav) {
            return "You really like me!";
        }
        else {
            return "Do you like me?";
        }
    }


    // Using <dom-repeat> for template repeating
    // The template repeater (dom-repeat) is a specialized template that binds to an array.
    // It creates one instance of the template's contents for each item in the array.
    static get template() {
        return html`

        <h3>Statically-configured icon-toggles</h3>

        <custom-icon-toggle toggle-icon="star"></custom-icon-toggle>
        <custom-icon-toggle toggle-icon="star" pressed></custom-icon-toggle>

        <h3>Data-bound icon-toggle</h3>

        <!-- use a computed binding to generate the message -->
        <div><span>[[message(fav)]]</span></div>

        <!-- curly brackets ({{}}} allow two-way binding -->
        <!--如果class中没有fav属性，会在这个自定义dom中自动创建一个fav属性-->
        <custom-icon-toggle toggle-icon="favorite" pressed="{{fav}}"></custom-icon-toggle>
        
        `;
    }
}
customElements.define(CustomElement.is, CustomElement);
