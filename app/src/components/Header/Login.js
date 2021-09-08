import React, { Component } from "react";
import Modal from 'react-bootstrap/Modal';
import ModalHeader from 'react-bootstrap/ModalHeader';
import ModalTitle from 'react-bootstrap/ModalTitle';
import ModalBody from 'react-bootstrap/ModalBody';
import ModalFooter from 'react-bootstrap/ModalFooter';
import Button from 'react-bootstrap/Button';
import Form from "react-bootstrap/Form";

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {

        };
    }

    render() {
        let loginFailedMessage = null;
        if (this.props.loginFailed) {
            loginFailedMessage = (
                <div>
                    <p id={"login-failed-warning"}>Incorrect email/password.</p>
                </div>
            );
        }

        return (
            <Modal.Body className={"auth-model-body"}>
                <div className={"auth-modal-body"}>
                    <h3 className={"auth-modal-body-title"}>Log in to your account</h3>
                    <Form className={"auth-form"} >
                        <Form.Group className="auth-form-group mb-3" controlId="formBasicEmail">
                            <Form.Control id={"login-username-input"} className={"auth-form-input"} size="sm" type="text" placeholder="Email" required/>
                        </Form.Group>
                        <Form.Group className={"auth-form-group mb-3"} controlId="formBasicPassword">
                            <Form.Control id={"login-password-input"} className={"auth-form-input"} size="sm" type="password" placeholder="Password" required/>
                        </Form.Group>
                    </Form>
                    {loginFailedMessage}
                </div>
            </Modal.Body>
        );
    }
}

export default Login;
