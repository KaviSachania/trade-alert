import React, { Component } from "react";
import Modal from 'react-bootstrap/Modal';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import FloatingLabel from 'react-bootstrap/FloatingLabel';
import axios from "axios";

class Register extends Component {
    constructor(props) {
        super(props);
        this.state = {

        };
    }

    render() {
        if (this.props.displayEmailMessage) {
            return (
                <Modal.Body className={"model-body"}>
                    <div>
                        <h4 className={"auth-modal-body-byline"}>Success! Check your email to confirm your registration.</h4>
                    </div>
                </Modal.Body>
            );
        }

        let isInvalidEmail = (this.props.invalidEmail || this.props.usernameExists);

        return (
            <Modal.Body>
                <div className={"auth-modal-body"}>
                    <h3 className={"auth-modal-body-title"}>Create an account</h3>
                    <Form className={"auth-form"}>
                        <Form.Group className="auth-form-group mb-3" controlId="formBasicEmail">
                            <Form.Control
                                id={"registration-username-input"}
                                className={"auth-form-input"}
                                size="sm"
                                type="text"
                                placeholder="Email"
                                isInvalid={isInvalidEmail}
                                required
                            />
                            {this.props.invalidEmail ?
                                <Form.Control.Feedback type="invalid">
                                    Please enter a valid email.
                                </Form.Control.Feedback> :
                                <Form.Control.Feedback type="invalid">
                                    Account with email already exists.
                                </Form.Control.Feedback>
                            }
                        </Form.Group>
                        <Form.Group className={"auth-form-group mb-3"} controlId="formBasicPassword">
                            <Form.Control
                                id={"registration-password-input"}
                                className={"auth-form-input"}
                                size="sm"
                                type="password"
                                placeholder="Password"
                                isInvalid={this.props.passwordMissing}
                                required/>
                            <Form.Control.Feedback type="invalid">
                                Password required.
                            </Form.Control.Feedback>
                        </Form.Group>
                    </Form>
                </div>
            </Modal.Body>
        );
    }
}

export default Register;
