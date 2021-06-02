import { useEffect, useState } from "react";

export function EventList() {

    let [events, updateEvents] = useState(['Hello World', 'Goodbye World']);

    let [eSrc, updateSource] = useState(new EventSource('http://localhost:8080/notices'));

    useEffect(
        () => { eSrc.onmessage = (evt) => updateEvents([...events, evt.data]); },
        [eSrc, events]
    )


    return (
        <ul>
            { events.map(s => <li>{s}</li>) }
        </ul>
    );
}