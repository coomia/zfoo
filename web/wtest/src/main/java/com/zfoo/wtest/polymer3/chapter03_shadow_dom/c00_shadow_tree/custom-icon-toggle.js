import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import '@polymer/iron-icon/iron-icon.js';
import '@polymer/iron-icons/iron-icons.js';

class CustomIconToggle extends PolymerElement {
    static get template() {
        return html`
            <!-- Begin shadow tree -->
            <!--CSS placed in the shadow tree is scoped to the shadow tree, and won't leak out to other parts of your DOM.-->
            <style>
    
                /*You can think of a custom property as a variable that can be substituted in to your CSS rules:*/
                /*自定义css属性变量*/
    
                :host {
                    /*未选中的颜色*/
                    --icon-toggle-color: lightgrey;
                    /*选中的颜色*/
                    --icon-toggle-pressed-color: red;
                    /*边缘的轮廓颜色*/
                    --icon-toggle-outline-color: black;
                    /*字体*/
                    font-family: sans-serif;
    
                    /* Set values for CSS mixin */
                    --my-minin-theme: {
                        text-decoration: underline;
                        font-style: italic;
                        color: var(--my-theme-color);
                    };
    
                }
    
                :host {
                    display: inline-block;
                }
    
                /*You can use CSS selectors to determine when and how to style the host. In this code sample:*/
                /*The selector :host matches any <custom-element> element*/
                /*The selector :host(.red) matches <custom-element> elements of class red*/
                /*The selector :host(:hover) matches <custom-element> elements when they are hovered over*/
                :host iron-icon {
                    fill: var(--icon-toggle-color, rgba(0, 0, 0, 0));
                    stroke: var(--icon-toggle-outline-color, currentcolor);
                }
    
                :host([pressed]) iron-icon {
                    fill: var(--icon-toggle-pressed-color, currentcolor);
                }
    
                /*上一层的h2不会影响这一次的h2*/
                h2 {
                    color: grey;
                }
    
                h4 {
                    @apply --my-minin-theme;
                }
    
                p {
                    color: grey;
                }
    
            </style>
    
            <!--<custom-icon-toggle>叫shadow host，整个被<custom-icon-toggle>包起来的叫shadow tree-->
    
            <!--#shadow-root-->
            <!--如果上下层都有指定的style，使用当前dom tree里的style，这是polymer的css覆盖-->
            <!--灰色-->
            <h2>h2, use shadow dom tree styles:</h2>
    
            <!--如果当前的dom tree里没有指定的style，使用上一层的style，这是polymer的css继承-->
            <!--红色-->
            <h3>h3, use inheritance from document-level styles:</h3>
    
    
            <h4>h4,use css mixins styles:</h4>
    
            <iron-icon icon="[[toggleIcon]]" on-click="toggle"></iron-icon>
    
            <!--Think of the <slot> as a placeholder showing where child nodes will render.-->
            <!--HTML <slot> 标签是web组件技术的一部分，slot是web组件的一个占位符，可以用来插入自定义的标记文本。可以创建不同的DOM树并进行渲染。-->
            <slot></slot>
            <!-- End shadow tree -->

        `;
    }

    static get is() {
        return 'custom-icon-toggle';
    }

    // To declare properties, add a static properties getter to the element's class. The getter should return an object containing property declarations.
    // In most cases, a property that's part of your element's public API should be declared in the properties object.
    static get properties() {
        return {
            pressed: {
                type: Boolean,
                notify: true,
                reflectToAttribute: true,
                value: false,
            },
            toggleIcon: {
                type: String
            }
        };
    }


    static get observers() {
        return [
            'pressedChanged(pressed)'
        ]
    }

    pressedChanged(pressed) {
        alert("pressed=" + pressed);
    }

    toggle() {
        this.pressed = !this.pressed;
    }

}

customElements.define(CustomIconToggle.is, CustomIconToggle);