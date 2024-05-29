import { useState } from "react";

export default function JoinRoom() {
  const [room, setRoom] = useState<null | string>(null);

  const onSubmit = () => {
    window.location.assign(`/#/video/#/${room}`);
  };

  return (
    <div>
      <input type="text" onChange={(e) => setRoom(e.target.value)} />
      <button onClick={onSubmit}>Join</button>
    </div>
  );
}
