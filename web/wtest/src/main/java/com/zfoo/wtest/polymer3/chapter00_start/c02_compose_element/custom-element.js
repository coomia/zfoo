import {PolymerElement, html} from '../../node_modules/@polymer/polymer/polymer-element.js';

class CustomElement extends PolymerElement {

    static get is() {
        return "custom-element";
    }

    static get template() {
        return html`
        
        <!-- scoped CSS for this element -->
        <!--Note: The CSS styles defined inside the <dom-module> are scoped to the element's shadow DOM. 
        So the div rule here only affects <div> tags inside <custom-element>.-->
        <!--设置h1标签的颜色为绿色，不会被外部的设置影响-->
        <style>
            h1 {
                color: green;
            }
        </style>
        
        
        <div>
            <!--Shadow DOM lets you control composition. The element's children can be distributed so they render as if they were inserted into the shadow DOM tree.-->
            <!--This example creates a simple tag that decorates an image by wrapping it with a styled <div> tag.-->
            <!--HTML <slot> 标签是web组件技术的一部分，slot是web组件的一个占位符，可以用来插入自定义的标记文本。可以创建不同的DOM树并进行渲染。-->   
            <slot></slot>
        </div>
        `;
    }
}

// Register the new element
customElements.define(CustomElement.is, CustomElement);