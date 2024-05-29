function BoardIcon(props: object) {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 30 30"
      width="50"
      height="50"
      fill="#ffffff"
      {...props}
    >
      <g opacity="100%" filter="url(#filter_dshadow_0_0_0_03030314)">
        <rect
          x="0"
          y="0"
          width="30"
          height="30"
          rx="2"
          fill="url(#image-pattern-c56a2962-6760-428e-b9a9-7d48fe5597b4)"
        ></rect>
        <defs>
          <pattern
            id="image-pattern-c56a2962-6760-428e-b9a9-7d48fe5597b4"
            patternContentUnits="objectBoundingBox"
            width="1"
            height="1"
          >
            <use
              href="#image_https://assets.api.uizard.io/api/cdn/stream/b1d05642-ae18-4190-8f2b-4fe7c52ee8c6.png"
              transform="matrix(0.0044444444444444444 0 0 0.0044444444444444444 0 0)"
            ></use>
          </pattern>
        </defs>
      </g>
      <defs>
        <filter
          id="filter_dshadow_0_0_0_03030314"
          colorInterpolationFilters="sRGB"
          filterUnits="userSpaceOnUse"
        >
          <feFlood floodOpacity="0" result="bg-fix"></feFlood>
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="alpha"
          ></feColorMatrix>
          <feOffset dx="0" dy="0"></feOffset>
          <feGaussianBlur stdDeviation="0"></feGaussianBlur>
          <feComposite in2="alpha" operator="out"></feComposite>
          <feColorMatrix
            type="matrix"
            values="0 0 0 0 0.011764705882352941 0 0 0 0 0.011764705882352941 0 0 0 0 0.011764705882352941 0 0 0 0.08 0"
          ></feColorMatrix>
          <feBlend
            mode="normal"
            in2="bg-fix"
            result="bg-fix-filter_dshadow_0_0_0_03030314"
          ></feBlend>
          <feBlend
            in="SourceGraphic"
            in2="bg-fix-filter_dshadow_0_0_0_03030314"
            result="shape"
          ></feBlend>
        </filter>
        <image
          id="image_https://assets.api.uizard.io/api/cdn/stream/b1d05642-ae18-4190-8f2b-4fe7c52ee8c6.png"
          width="225"
          height="225"
          href="data:image/png;base64,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"
        ></image>
      </defs>
    </svg>
  );
}

export default BoardIcon;
