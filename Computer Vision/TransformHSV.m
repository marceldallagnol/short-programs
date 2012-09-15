function imgOut = TransformHSV(imgIn,theta,s,v)
sizeImg = size(imgIn);
imgOut = zeros(sizeImg);
for i = 1:sizeImg(1)
    for j = 1:sizeImg(2)
        r = double(imgIn(i,j,1))/255.0;
        g = double(imgIn(i,j,2))/255.0;
        b = double(imgIn(i,j,3))/255.0;
        maxC = max([r g b]);
        V = v * maxC;
        if V == 0
            imgOut(i,j,:) = [0 0 0];
        else
            r = r/V;
            g = g/V;
            b = b/V;
            maxC = max([r g b]);
            minC = min([r g b]);
            S = s * (maxC - minC);
            if S == 0
                imgOut(i,j,:) = [0 0 V];
            else
                r = (r - minC) / (maxC - minC);
                g = (g - minC) / (maxC - minC);
                b = (b - minC) / (maxC - minC);
                maxC = max([r g b]);
                
                if maxC == r
                    h = mod(60.0 * (g - b) - theta, 360.0);
                elseif maxC == g
                    h = mod(120.0 + 60.0 * (b - r) - theta, 360.0);
                else
                    h = mod(240. + 60.0 * (r - g) - theta, 360.0);
                end
                imgOut(i,j,:) = [h/360.0 S V];
            end
        end
    end
end
imgOut = hsv2rgb(imgOut);
end