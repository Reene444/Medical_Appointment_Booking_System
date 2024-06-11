import React, { useState, useEffect } from 'react';
import axios from 'axios';

function AppointmentList({ onSelectAppointment }) {
    const [appointments, setAppointments] = useState([]);

    useEffect(() => {
        const fetchAppointments = async () => {
            try {
                const response = await axios.get('http://localhost:8095/api/v1/appointments');
                setAppointments(response.data);
            } catch (error) {
                console.error('Error fetching appointments:', error);
            }
        };

        fetchAppointments();
    }, []);

    return (
        <div>
            <h2>All Appointments</h2>
            <ul>
                {appointments.map((appointment) => (
                    <li key={appointment.appointment_id} onClick={() => {onSelectAppointment(appointment.appointment_id);console.log(appointment.appointment_id)}}>
                        {appointment.appointment_id} :  {appointment.doctor_name} with {appointment.patient} on {appointment.date} at {appointment.time}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default AppointmentList;