function imgOut = hsvtorgb(imgIn)
sizeImg = size(imgIn);
imgOut = zeros(sizeImg);
for i = 1:sizeImg(1)
    for j = 1:sizeImg(2)
        h = imgIn(i,j,1) * 360.0;
        s = imgIn(i,j,2);
        v = imgIn(i,j,3);
        
        if s == 0
            imgOut = [v v v];
       
        else
            h = h/60.;
            k = floor(h);
            f = h - k;
            p = v * (1-s);
            q = v * (1-s*f);
            t = v * (1-s*(1-f));
            
            if k == 0
                r = v;
                g = t;
                b = p;
            elseif k == 1
                r = q;
                g = v;
                b = p;
            elseif k == 2
                r = p;
                g = v;
                b = t;
            elseif k == 3
                r = p;
                g = q;
                b = v;
            elseif k == 4
                r = t;
                g = p;
                b = v;
            else
                r = v;
                g = p;
                b = q;
            end
            imgOut(i,j,:) = [r g b];
        end 
    end
end
end