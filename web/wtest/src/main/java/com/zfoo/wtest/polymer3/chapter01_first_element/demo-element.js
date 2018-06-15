import { PolymerElement, html } from '@polymer/polymer/polymer-element.js';
import './icon-toggle.js';

class DemoElement extends PolymerElement {
    static get is() {
        return "demo-element"
    }

    _message(fav) {
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

        <icon-toggle toggle-icon="star"></icon-toggle>
        <icon-toggle toggle-icon="star" pressed></icon-toggle>

        <h3>Data-bound icon-toggle</h3>

        <!-- use a computed binding to generate the message -->
        <div><span>[[_message(fav)]]</span></div>

        <!-- curly brackets ({{}}} allow two-way binding -->
        <!--如果class中没有fav属性，会在这个自定义dom中自动创建一个fav属性-->
        <icon-toggle toggle-icon="favorite" pressed="{{fav}}"></icon-toggle>
        
        `;
    }
}
customElements.define('demo-element', DemoElement);
