import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import '@polymer/iron-icon/iron-icon.js';
import '@polymer/iron-icons/iron-icons.js';

class CustomIconToggle extends PolymerElement {
    static get template() {
        return html`
        <!-- Begin shadow tree -->

            <style>
                :host {
                    /*未选中的颜色*/
                    --icon-toggle-color: lightgrey;
                    /*选中的颜色*/
                    --icon-toggle-pressed-color: red;
                    /*边缘的轮廓颜色*/
                    --icon-toggle-outline-color: black;
                    /*字体*/
                    font-family: sans-serif;
                }
    
                :host {
                    display: inline-block;
                }
    
                :host iron-icon {
                    fill: var(--icon-toggle-color, rgba(0, 0, 0, 0));
                    stroke: var(--icon-toggle-outline-color, currentcolor);
                }
    
                :host([pressed]) iron-icon {
                    fill: var(--icon-toggle-pressed-color, currentcolor);
                }
    
                /*selects all slotted content:*/
                ::slotted(*) {
                    color: grey;
                }
    
                /*::slotted(.classname)*/
            </style>
    
            <!--默认的情况下，自定义元素标签里不能含有其它标签，如果要包含其它标签，需要自定义一个<slot>占位符标签。-->
            <!--会通过name属性匹配-->
            <slot name="title"></slot>
    
            <h2>custom-icon-toggle:</h2>
            <iron-icon icon="[[toggleIcon]]" on-click="toggle"></iron-icon>
    
            <!--如果没有name属性匹配所有-->
            <slot></slot>
    
            <slot name="footer">
                <!--如果找不到匹配的元素，显示下面这个默认的div-->
                <div>default footer text!</div>
            </slot>
            
        `;
    }

    static get is() {
        return 'custom-icon-toggle';
    }

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
