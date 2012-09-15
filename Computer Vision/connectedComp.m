function imgOut = connectedComp(imgIn)
global count marks img height width
count = 0;
img = imgIn;
[height width] = size(img);
marks = zeros(height,width);
for x = 1:height
    for y = 1:width
        if img(x,y) && ~marks(x,y)
            count = count + 1;
            label(x,y);
        end
    end
end

imgOut = marks/count;
end

function label(x,y)
global count marks img height width
marks(x,y) = count;
top = x > 1;
bot = x <= height;
left = y > 1;
right = y <= width;

if top && img(x-1,y) && ~marks(x-1,y)
    label(x-1,y);
end
if bot && img(x+1,y) && ~marks(x+1,y)
    label(x+1,y);
end
if left && img(x,y-1) && ~marks(x,y-1)
    label(x,y-1);
end
if right && img(x,y+1) && ~marks(x,y+1)
    label(x,y+1);
end
if top && left && img(x-1,y-1) && ~marks(x-1,y-1)
    label(x-1,y-1);
end
if top && right && img(x-1,y+1) && ~marks(x-1,y+1)
    label(x-1,y+1);
end
if bot && left && img(x+1,y-1) && ~marks(x+1,y-1)
    label(x+1,y);
end
if bot && right && img(x+1,y+1) && ~marks(x-1,y+1)
    label(x+1,y+1);
end
end