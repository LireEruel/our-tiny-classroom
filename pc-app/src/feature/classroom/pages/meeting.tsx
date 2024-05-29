import { useEffect } from "react";

export default function Video() {
  const id = "test_room1";
  useEffect(() => {
    const domain = "https://ssafya.daily.co/";

    const script = document.createElement("script");
    script.innerHTML = `window.DailyIframe.createFrame({
      iframeStyle: {
        width: "100%",
        height: "99%",
        position: "absolute",
      }
    }).join({url: "${domain}${id}"})`;

    document.body.appendChild(script);
  }, [id]);
  return <div></div>;
}
