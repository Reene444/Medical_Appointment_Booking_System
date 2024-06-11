import React, { useState } from 'react';
import './App.css'; // 导入 CSS 文件
import AppointmentForm from './components/AppointmentForm';
import AppointmentList from './components/AppointmentList';
import AppointmentDetails from './components/AppointmentDetails';

function App() {
    const [appointment_id, setAppointment_id] = useState(null);

    const handleAppointmentAdded = () => {
        setAppointment_id(null);
    };

    const handleSelectAppointment = (appointment_id) => {
        setAppointment_id(appointment_id);
    };

    return (
        <div>
            <h1>Medical Appointment Booking System</h1>
            <AppointmentForm onAppointmentAdded={handleAppointmentAdded} />
            <AppointmentList onSelectAppointment={handleSelectAppointment} />
            <AppointmentDetails appointment_id={appointment_id} />
        </div>
    );
}

export default App;