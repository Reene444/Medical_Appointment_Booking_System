import React, { useState, useEffect } from 'react';
import axios from 'axios';

function AppointmentDetails({ appointment_id }) {
    const [appointment, setAppointment] = useState(null);

    useEffect(() => {
        const fetchAppointment = async () => {
            if (appointment_id) {
                try {
                    console.log('Fetching details for appointment ID:', appointment_id); // 添加日志以检查 ID
                    const response = await axios.get(`http://localhost:8095/api/v1/appointments/${appointment_id}`);
                    setAppointment(response.data);
                } catch (error) {
                    console.error('Error fetching appointment details:', error);
                }
            }
        };

        fetchAppointment();
    }, [appointment_id]); // 当 appointment_id 变化时，重新执行此 effect

    const handleCancel = async () => {
        try {
            await axios.delete(`http://localhost:8095/api/v1/appointments/${appointment_id}`);
            setAppointment(null);
        } catch (error) {
            console.error('Error canceling appointment:', error);
        }
    };

    if (!appointment) {

        return <div>Select an appointment to see details</div>;
    }

    return (
        <div>
            <h2>Appointment Details</h2>
            <p>Patient: {appointment.patient_name}</p>
            <p>Doctor: {appointment.doctor_name}</p>
            <p>Date: {appointment.date}</p>
            <p>Time: {appointment.time}</p>
            <button onClick={handleCancel}>Cancel Appointment</button>
        </div>
    );
}

export default AppointmentDetails;