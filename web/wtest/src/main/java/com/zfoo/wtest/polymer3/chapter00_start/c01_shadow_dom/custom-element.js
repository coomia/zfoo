// PolymerElement is the base Polymer element class
// html is a helper function that parses a JavaScript template literal. More about this soon.


import {PolymerElement, html} from '../../node_modules/@polymer/polymer/polymer-element.js';


class CustomElement extends PolymerElement {
    // You then give your new element a name, so that the browser can recognize it when you use it in tags.
    // This name must match the id given in your element's template definition (<dom-module id="icon-toggle">).
    static get is() {
        return "custom-element";
    }

    // Shadow DOM is encapsulated inside the element.
    // Polymer's DOM templating to create a shadow DOM tree for your element.
    // ShadowDOM最大的用处应该是隔离外部环境用于封装组件,它有一种既定的样式，外部css不会影响到它
    static get template() {
        return html`
        
        <!--设置h1标签的颜色为绿色，不会被外部的设置影响-->
        <style>
            h1 {
                color: green;
            }
        </style>
        
        <h1>A heading!</h1>
        <p>I'm a DOM element. This is my shadow DOM!</p>
        
        `;
    }
}

// Register the new element
customElements.define(CustomElement.is, CustomElement);