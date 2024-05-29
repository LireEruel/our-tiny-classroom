function LogoutIcon() {
  return (
    <div>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        xmlnsXlink="http://www.w3.org/1999/xlink"
        viewBox="0 0 21 25"
        width="21"
        height="25"
        fill="none"
      >
        <g opacity="100%" filter="url(#filter_dshadow_0_0_0_03030314)">
          <rect
            x="0"
            y="0"
            width="21"
            height="25"
            rx="2"
            fill="url(#image-pattern-ef099dc2-a84c-4b88-b142-33883b3bdd67)"
          ></rect>
          <defs>
            <pattern
              id="image-pattern-ef099dc2-a84c-4b88-b142-33883b3bdd67"
              patternContentUnits="objectBoundingBox"
              width="1"
              height="1"
            >
              <use
                href="#image_https://assets.api.uizard.io/api/cdn/stream/b0a7a4f4-05e8-4384-88b7-667b10b19701.png"
                transform="matrix(0.005314625850340137 0 0 0.004464285714285714 -0.09789540816326538 0)"
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
            id="image_https://assets.api.uizard.io/api/cdn/stream/b0a7a4f4-05e8-4384-88b7-667b10b19701.png"
            width="225"
            height="224"
            href="data:image/png;base64,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"
          ></image>
        </defs>
      </svg>
    </div>
  );
}

export default LogoutIcon;
